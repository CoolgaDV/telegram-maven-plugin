#!/bin/bash

# Release script takes one argument - release type. It should be either 'major' or 'minor'.
# Script does the following things:
# 1. Truncates '-SNAPSHOT' postfix for current version
# 2. Builds project and deploys artifact to Bintray repository
# 3. Makes commit with appropriate release tag and push to VCS
# 4. Sets up next development version
# 5. Makes commit and pushes to VCS

# Any command failure should terminate the script
set -e

function info {
    printf "\n$1\n"
}

# Additional grep filtering is used for truncating Maven logging messages (like '[INFO] ...')
# and messages about downloading dependencies. So as output we will get something like '1.0'
function get_project_version {
    mvn org.apache.maven.plugins:maven-help-plugin:2.2:evaluate -Dexpression=project.version | \
    grep -Ev '(^\[|Download\w+:)'
}

# Parameter $1 contains an expression with variables produced by build-helper plugin call
function change_project_version {
    mvn build-helper:parse-version \
        versions:set -DnewVersion=$1 \
        versions:commit
}

if [ "$#" -ne 1 ]; then
    info "Release script takes one argument - release type. It should be either 'major' or 'minor'."
    exit 1
fi

release_type=$1

if [ "$release_type" != "major" ] && [ "$release_type" != "minor" ]; then
    info "Release script argument should be either 'major' or 'minor'"
    exit 1
fi

info "Project release: ${release_type}"
info "Setting release version...\n"

change_project_version "\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}"

release_version=$(get_project_version)

info "Release version is set: ${release_version}"
info "Building and publishing release...\n"

mvn clean deploy

info "Release build is done"
info "Committing and pushing changes to VCS...\n"

git add -A
git commit -m "Creating release version $release_version"
git tag -a "v$release_version" -m "Release version $release_version"
git push origin HEAD:master --tags

info "Changes are committed and pushed"
info "Setting next development version...\n"

if [ "$release_type" = "major" ]; then
    # Major version increment leads to zeroing of minor version
    change_project_version "\${parsedVersion.nextMajorVersion}.0-SNAPSHOT"
else
    change_project_version "\${parsedVersion.majorVersion}.\${parsedVersion.nextMinorVersion}-SNAPSHOT"
fi

next_development_version=$(get_project_version)

info "Next development version ${next_development_version}"
info "Committing and pushing changes to VCS...\n"

git add -A
git commit -m "Setting next development version $next_development_version"
git push origin HEAD:master

info "Changes are committed and pushed"
info "Project release is finished"