#!/bin/bash

#=================================================================
# HornetQ-Tests
#
# 
# @description: Setup script for HornetQ-Tests
# @author: Elisha Lai
# @version: 1.0 29/06/2017
#=================================================================

# Clean directory
echo "Cleaning directory..."
rm -rf bin
rm -rf config

# Download HornetQ distribution
echo "Downloading HornetQ distribution..."
wget http://downloads.jboss.org/hornetq/hornetq-2.4.0.Final-bin.tar.gz

# Unzip HornetQ distribution
echo "Unzipping HornetQ distribution..."
tar -xvzf hornetq-2.4.0.Final-bin.tar.gz

# Move HornetQ binaries and configurations to the project directory
echo "Moving HornetQ binaries and configurations to the project directory..."
mv hornetq-2.4.0.Final/bin bin
mv hornetq-2.4.0.Final/config config

# Remove unnecessary files
echo "Removing unnecessary files..."
rm -rf hornetq-2.4.0.Final-bin.tar.gz
rm -rf hornetq-2.4.0.Final

# Install packages based on platform
OS="`uname`"
case $OS in
  'Linux')
    # Install AIO package
    echo "Installing AIO package..."
    sudo apt-get install libaio1
    # Install Maven package
    echo "Installing Maven package..."
    sudo apt-get install maven
    ;;
  'Darwin')
    # Check if Homebrew is already installed
    which -s brew
    if [[ $? != 0 ]] ; then
      # Installing Homebrew
      echo "Installing Homebrew..."
      ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
    fi
    # Install Maven package
    echo "Installing Maven package..."
    brew install maven
    ;;
  *)
    echo "Please consult documentation regarding installing AIO and Maven package for your platform."
    ;;
esac

# Validate project directory
echo "Validating project directory..."
mvn validate
