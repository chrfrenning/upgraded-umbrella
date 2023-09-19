---
title: "IN5020 - Distributed Systems - Assignment 1"
author: Group 7 (aeturzo@ifi.uio.no, alinal@student.matnat.uio.no, chrifren@ifi.uio.no)
date: September 19, 2023
geometry: a4paper, margin=1.5cm
output: pdf_document
---

\tableofcontents
\newpage



# Introduction and design

This programming assignment follows the instructions of the assignment carefully and implements a Java RMI server, a load balancer and a client. The server is set up to run a simulation on a single machine. The NetworkSimulator class is the main class to run the server, which spins up both the LoadBalancer (which owns the registry) and 5 instances of the server (in zones named 1-5).

The client executes the instructions in the ./dataset/input.txt. The server expects to find the dataset in ./dataset/dataset.csv.

We chose to let both the Client and the Servers use the same Registry (hosted by the LoadBalancer). The LoadBalancer is responsible for distributing the work to the servers, and the servers are responsible for executing the work and returning the results to the client.

The Servers use a threadpool (with a single execution thread) that is used to execute the remote method callsin a FIFO order (guaranteed by the Java implementaiton of the ThreadPool). Calls to getQueueLength(), which is called by the LoadBalancer, are handled on the main thread and will therefore not interfere with the execution of the long-running statistics remote method calls.



# Screenshots while running

![Screenshot of the system in action](./run.png) \


# Project and workload distribution


# How to run


