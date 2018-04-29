#!/bin/bash

###############################################################################################
# INFO: DO THE FOLLOWING BEFORE RUNNING THIS SCRIPT!!
# Add all dependencies in AsteroidsNetbeansPlatform-app to Core, player, enemy, etc.
# Run the generate-update-center.sh
# Copy application/netbeans_site -> UpdateCenter/netbeans_site
# Remove all dependencies in AsteroidsNetbeansPlatform-app to Core, player, enemy etc.
# Add the SilentUpdate to the AsteroidsNetbeansPlatform-app
# Clean and Build from within Netbeans IDE
# Run the asteroids game
###############################################################################################

cd application; mvn -Pdeployment clean install
