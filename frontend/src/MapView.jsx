import { useEffect, useState, useRef } from "react";
import axios from "axios";
import { MapContainer, TileLayer, CircleMarker, Popup } from "react-leaflet";

const API = "http://localhost:8080/api";

export default function MapView() {
  const [accidents, setAccidents] = useState([]);
  const [hotspots, setHotspots] = useState([]);
  const [month, setMonth] = useState("");
  const [severity, setSeverity] = useState("");

  const mapRef = useRef(null);

  useEffect(() => {
    const params = {};
    if (month) params.month = month;
    if (severity) params.severity = severity;

    axios.get(`${API}/accidents`, { params })
      .then(res => setAccidents(res.data))
      .catch(err => console.error(err));

    axios.get(`${API}/hotspots`)
      .then(res => setHotspots(res.data))
      .catch(err => console.error(err));
  }, [month, severity]);

  return (
    <div style={{ display: "grid", gridTemplateColumns: "300px 1fr", height: "100vh" }}>
      
      {/* Sidebar */}
      <div style={{ padding: 16, borderRight: "1px solid #ddd", overflowY: "auto" }}>
        <h2>Berlin Cyclist Hotspots</h2>

        <label>Month</label>
        <select 
          value={month} 
          onChange={e => setMonth(e.target.value)} 
          style={{ width: "100%", marginBottom: 12 }}
        >
          <option value="">All</option>
          {[...Array(12)].map((_, i) => (
            <option key={i + 1} value={i + 1}>{i + 1}</option>
          ))}
        </select>

        <label>Severity</label>
        <select 
          value={severity} 
          onChange={e => setSeverity(e.target.value)} 
          style={{ width: "100%" }}
        >
          <option value="">All</option>
          <option value="1">Fatal (1)</option>
          <option value="2">Serious (2)</option>
          <option value="3">Slight (3)</option>
        </select>

        <p style={{ marginTop: 16 }}>
          Showing <b>{accidents.length}</b> accidents
        </p>

        <hr />

        <h3> Top Hotspots</h3>

        {hotspots.slice(0, 5).map((h, i) => (
          <div
            key={i}
            style={{
              padding: 8,
              marginBottom: 6,
              borderRadius: 4,
              background: "#f5f5f5",
              cursor: "pointer"
            }}
          >
            <b>{i + 1}. Cluster {h.cluster}</b><br />
            Risk Score: {h.riskScore}<br />
            Total Accidents: {h.total}
          </div>
        ))}
      </div>

      {/* Map */}
      <div style={{ height: "100vh", width: "100%" }}>
        <MapContainer 
          center={[52.52, 13.405]} 
          zoom={11} 
          style={{ height: "100%", width: "100%" }}
          whenCreated={(map) => { mapRef.current = map; }}
        >
          <TileLayer
            attribution="&copy; OpenStreetMap contributors"
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />

          {accidents.map((a, i) => (
            <CircleMarker
              key={i}
              center={[a.lat, a.lon]}
              radius={a.cluster === -1 ? 2 : 6}
              pathOptions={{
              opacity: 0.8,
              color: a.cluster === -1 ? "#2b6cb0" : "#e53e3e", // blue for normal, red for hotspot
              fillOpacity: a.cluster === -1 ? 0.6 : 0.9
               }}
            >
              <Popup>
                <div>
                  <div><b>Cluster:</b> {a.cluster}</div>
                  <div><b>Month:</b> {a.month}</div>
                  <div><b>Severity:</b> {a.severity}</div>
                </div>
              </Popup>
            </CircleMarker>
          ))}
        </MapContainer>
      </div>

    </div>
  );
}