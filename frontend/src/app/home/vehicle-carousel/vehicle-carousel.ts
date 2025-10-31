import { Component, input, signal, computed, effect, OnDestroy, DestroyRef, inject } from '@angular/core';
import { Vehicle } from '@models';
import { VehicleCard } from '@vehicles/vehicle-card/vehicle-card';

@Component({
  selector: 'app-vehicle-carousel',
  standalone: true,
  imports: [VehicleCard],
  templateUrl: './vehicle-carousel.html',
  styleUrl: './vehicle-carousel.scss'
})
export class VehicleCarousel implements OnDestroy {

  readonly vehicles = input.required<Vehicle[]>();
  
  // Signals para estado reactivo
  readonly currentIndex = signal(0);
  private intervalId: ReturnType<typeof setInterval> | undefined;
  private readonly destroyRef = inject(DestroyRef);

  // Computed para valores derivados (mejor práctica que getters)
  readonly visibleVehicles = computed(() => {
    const index = this.currentIndex();
    const allVehicles = this.vehicles();
    const vehiclesPerSlide = this.vehiclesPerSlide();
    
    if (allVehicles.length <= vehiclesPerSlide) {
      return allVehicles;
    }

    const visible: Vehicle[] = [];
    for (let i = 0; i < vehiclesPerSlide; i++) {
      const vehicleIndex = (index + i) % allVehicles.length;
      visible.push(allVehicles[vehicleIndex]);
    }
    return visible;
  });

  readonly vehiclesPerSlide = computed(() => {
    // Responsive: muestra diferentes cantidades según el ancho
    if (globalThis.window === undefined) return 3;
    
    const width = globalThis.window.innerWidth;
    if (width < 768) return 1;
    if (width < 1024) return 2;
    return 3;
  });

  readonly totalSlides = computed(() => {
    const perSlide = this.vehiclesPerSlide();
    const totalVehicles = this.vehicles().length;
    return Math.ceil(totalVehicles / perSlide);
  });

  constructor() {
    // Auto-play del carousel con effect
    effect(() => {
      this.startAutoPlay();
    });

    // Limpiar en destroy automáticamente
    this.destroyRef.onDestroy(() => {
      this.stopAutoPlay();
    });
  }

  ngOnDestroy(): void {
    this.stopAutoPlay();
  }

  startAutoPlay(): void {
    this.stopAutoPlay();
    this.intervalId = setInterval(() => {
      this.next();
    }, 5000);
  }

  stopAutoPlay(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  next(): void {
    const allVehicles = this.vehicles();
    if (allVehicles.length === 0) return;
    
    this.currentIndex.update(index => 
      index >= allVehicles.length - 1 ? 0 : index + 1
    );
  }

  previous(): void {
    const allVehicles = this.vehicles();
    if (allVehicles.length === 0) return;
    
    this.currentIndex.update(index => 
      index <= 0 ? allVehicles.length - 1 : index - 1
    );
  }

  goToSlide(index: number): void {
    this.currentIndex.set(index);
    this.startAutoPlay();
  }
}
