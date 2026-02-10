package com.example.berlinhotspots.service;

import com.example.berlinhotspots.dto.HotspotDto;
import com.example.berlinhotspots.model.Accident;
import com.example.berlinhotspots.model.Hotspot;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataService {

    private final List<Accident> accidents = new ArrayList<>();
    private final List<Hotspot> hotspots = new ArrayList<>();

    public DataService() {
        loadAccidents();
        loadHotspots();
    }

    private void loadAccidents() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource("data/accidents_with_clusters.csv").getInputStream()
                )
        )) {
            String line = br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");

                Accident a = new Accident();
                a.lon = Double.parseDouble(p[0]);
                a.lat = Double.parseDouble(p[1]);
                a.month = Integer.parseInt(p[2]);
                a.severity = Integer.parseInt(p[3]);
                a.cluster = Integer.parseInt(p[4]);

                accidents.add(a);
            }


        } catch (Exception e) {
            System.err.println("Failed to load accidents CSV");
            e.printStackTrace();
        }
    }

    private void loadHotspots() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource("data/hotspots.csv").getInputStream()
                )
        )) {
            String line = br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");

                Hotspot h = new Hotspot();
                h.cluster = Integer.parseInt(p[0]);
                h.accidentCount = Integer.parseInt(p[1]);

                hotspots.add(h);
            }

        

        } catch (Exception e) {
            System.err.println("Failed to load hotspots CSV");
            e.printStackTrace();
        }
    }

    public List<Accident> getAccidents(Integer month, Integer severity) {
        return accidents.stream()
                .filter(a -> month == null || a.month == month)
                .filter(a -> severity == null || a.severity == severity)
                .toList();
    }

    public List<HotspotDto> getHotspots() {
        Map<Integer, List<Accident>> grouped =
                accidents.stream()
                        .filter(a -> a.cluster != -1)
                        .collect(Collectors.groupingBy(a -> a.cluster));

        List<HotspotDto> result = new ArrayList<>();

        for (var entry : grouped.entrySet()) {
            int cluster = entry.getKey();
            int fatal = 0, serious = 0, slight = 0;

            for (Accident a : entry.getValue()) {
                if (a.severity == 1) fatal++;
                else if (a.severity == 2) serious++;
                else if (a.severity == 3) slight++;
            }

            result.add(new HotspotDto(
                    cluster,
                    entry.getValue().size(),
                    fatal,
                    serious,
                    slight
            ));
        }

        result.sort((a, b) -> b.riskScore - a.riskScore);
        return result;
    }
}
