Urban Technology – Cyclist Hotspot Detection

This project analyzes cyclist accident data in Berlin (2021) to identify spatial accident hotspots using geospatial analysis and clustering techniques.

What This Project Does

Cleans and processes cyclist accident data

Applies DBSCAN clustering to detect recurring accident zones

Calculates severity-based risk scores

Ranks hotspots automatically

Visualizes results using an interactive web map (Spring Boot + React + Leaflet)

Methodology

Geospatial processing using Python (GeoPandas)

Kernel Density Estimation (KDE) to measure accident intensity

DBSCAN clustering for hotspot detection

Weighted severity-based risk scoring:

Risk = (Fatal × 5) + (Serious × 3) + (Slight × 1)

Tech Stack

Python (Data processing and clustering)

Spring Boot (Backend API)

React + Leaflet (Frontend visualization)
