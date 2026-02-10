🚲 Urban Technology – Cyclist Hotspot Detection

This project analyzes cyclist accident data in Berlin (2021) to identify spatial accident hotspots using geospatial analysis and clustering techniques.

What This Project Does

Cleans and processes cyclist accident data

Applies DBSCAN clustering to detect recurring accident zones

Calculates severity-based risk scores

Ranks hotspots automatically

Visualizes results using an interactive web map (Spring Boot + React + Leaflet)

Methodology

Geo-spatial processing using Python (GeoPandas)

Kernel Density Estimation (KDE) for risk intensity

DBSCAN for hotspot detection

Weighted risk scoring:
Risk = (Fatal × 5) + (Serious × 3) + (Slight × 1)

Tech Stack

Python (Data Processing & Clustering)

Spring Boot (Backend API)

React + Leaflet (Frontend Visualization)
