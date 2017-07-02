#!/usr/bin/env python

# =============================================================================
#  HornetQ-Tests
#
#  @author: Elisha Lai
#  @description: Utility script to plot the latencies
#  @version: 0.0.1 30/10/2016
# =============================================================================

import numpy as np
import matplotlib.pyplot as plt


def plot_histogram(data):
    pass


def plot_cdf(data):
    sorted_data = np.sort(data)
    num_data_points = data.size
    probability = np.array(range(num_data_points)) / float(num_data_points)
    plt.title("CDF of HornetQ End-to-End Latency")
    plt.xlabel("Latency (ms)")
    plt.ylabel("Probability")
    plt.plot(sorted_data, probability)
    plt.show()


def main():
    # Load data from the CSV file with the latencies
    latencies = np.loadtxt("latency.csv", delimiter=",", skiprows=1, usecols=3)
    plot_cdf(latencies)
    #p = 1. * arange(len(latencies)) / (len(latencies) - 1)
    #plt.plot(latencies_sorted, p, c='blue')
    # Compute histogram of the set of data
    #num_messages, latency = np.histogram(latencies, bins='auto', density=True)
    # Compute cumulative sum of
    #num_messages_cumulative = np.cumsum(num_messages)
    # Plot
    #plt.plot(latency[:-1], num_messages_cumulative, c='blue')


if __name__ == "__main__":
    main()
