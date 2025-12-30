import { Component, AfterViewInit, Input, Output, EventEmitter, ElementRef, ViewChild, OnChanges, SimpleChanges, inject } from '@angular/core';
import { Sucursal } from '@models';
import { SucursalStore } from '@shared/stores/sucursal-store';
import * as L from 'leaflet';
@Component({
  selector: 'app-map',
  imports: [],
  templateUrl: './map.html',
  styleUrl: './map.scss'
})
export class Map implements AfterViewInit, OnChanges {
  @ViewChild('mapContainer') mapContainer!: ElementRef;
  sucursalStore = inject(SucursalStore)
  @Input() lat: number | undefined;
  @Input() lng: number | undefined;
  @Input() readonly = false;
  @Output() coordsSelected = new EventEmitter<{lat: number, lng: number}>();

  private map: L.Map | undefined;
  private marker: L.Marker | undefined;

  ngAfterViewInit(): void {
    this.initMap();
  }

  ngOnChanges(changes: SimpleChanges): void {
    // Si cambian las coordenadas desde afuera, actualizar el marcador
    if (this.map && (changes['lat'] || changes['lng'])) {
      this.updateMarker();
    }
  }
  private initMap(): void {
    this.fixLeafletIcons();

    // Coordenadas por defecto (Argentina) si no vienen datos
    const initialLat = this.lat || -34.6037;
    const initialLng = this.lng || -58.3816;
    const zoomLevel = this.lat ? 15 : 5;

    this.map = L.map(this.mapContainer.nativeElement).setView([initialLat, initialLng], zoomLevel);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '© OpenStreetMap'
    }).addTo(this.map);

    if (this.lat && this.lng) {
      this.updateMarker();
    }

    // Escuchar clicks solo si NO es solo lectura
    if (!this.readonly) {
      this.map.on('click', (e: L.LeafletMouseEvent) => {
        const { lat, lng } = e.latlng;
        this.lat = lat;
        this.lng = lng;
        this.updateMarker();
        this.coordsSelected.emit({ lat, lng });
      });
    }
    this.mostrarMarcadores(this.sucursalStore.sucursales())
  }

  private updateMarker(): void {
    if (!this.lat || !this.lng || !this.map) return;

    if (this.marker) {
      this.marker.setLatLng([this.lat, this.lng]);
    } else {
      this.marker = L.marker([this.lat, this.lng]).addTo(this.map);
    }
    // Opcional: Centrar mapa al seleccionar
    // this.map.panTo([this.lat, this.lng]); 
  }

  private fixLeafletIcons() {
    const iconRetinaUrl = '/marker-icon-2x.png';
    const iconUrl = '/marker-icon.png';
    const shadowUrl = '/marker-shadow.png';
    const iconDefault = L.icon({
      iconRetinaUrl,
      iconUrl,
      shadowUrl,
      iconSize: [25, 41],
      iconAnchor: [12, 41],
      popupAnchor: [1, -34],
      tooltipAnchor: [16, -28],
      shadowSize: [41, 41]
    });
    L.Marker.prototype.options.icon = iconDefault;
  }

  mostrarMarcadores(sucursales: Sucursal[]) {
    if (!this.map) return
    const bounds = L.latLngBounds([]); // Crear límites vacíos

    sucursales.forEach(sucursal => {
      const marker = L.marker([sucursal.latitud, sucursal.longitud]).addTo(this.map!);
      marker.bindPopup(sucursal.ciudad);
      
      // Extender los límites para incluir este marcador
      bounds.extend([sucursal.latitud, sucursal.longitud]);
    });

    // Decirle al mapa que se ajuste a esos límites
    this.map.fitBounds(bounds); 
  }
}
