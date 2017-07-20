#!/bin/bash

#=================================================================
# HornetQ-Tests
#
# 
# @description: Setup script for HornetQ-Tests
# @author: Elisha Lai
# @version: 1.0 29/06/2017
#=================================================================

# Clean project directory
echo "Cleaning project directory..."
rm -rf bin
rm -rf config

# Download HornetQ distribution
echo "Downloading HornetQ distribution..."
wget http://downloads.jboss.org/hornetq/hornetq-2.4.0.Final-bin.tar.gz

# Unzip HornetQ distribution
echo "Unzipping HornetQ distribution..."
tar -xvzf hornetq-2.4.0.Final-bin.tar.gz

# Move HornetQ binaries, configurations and libraries to the project directory
echo "Moving HornetQ binaries, configurations and libraries to the project directory..."
mv hornetq-2.4.0.Final/bin bin
mv hornetq-2.4.0.Final/config config
mv hornetq-2.4.0.Final/lib lib

# Remove unnecessary files
echo "Removing unnecessary files..."
rm -rf hornetq-2.4.0.Final-bin.tar.gz
rm -rf hornetq-2.4.0.Final

# Install packages based on platform
OS="`uname`"
case $OS in
  'Linux')
    # Resynchronizing package index files from their sources
    echo "Resynchronizing package index files from their sources..."
    sudo apt-get update
    # Install AIO package
    echo "Installing AIO package..."
    sudo apt-get install libaio1
    # Install Maven package
    echo "Installing Maven package..."
    sudo apt-get install maven2
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

# Cleaning project directory
echo "Cleaning project directory..."
mvn clean

echo "Setup complete."
