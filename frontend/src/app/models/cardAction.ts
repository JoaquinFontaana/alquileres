// Acciones para tarjetas de vehículos
export enum VehicleCardAction {
  RENT = 'rent',
  EDIT = 'edit',
  DELETE = 'delete',
  VIEW = 'view'
}

// Acciones para tarjetas de alquileres
export enum RentalCardAction {
  VIEW = 'view',
  CANCEL = 'cancel',
  DELIVER = 'deliver',
  RETURN = 'return',
  RETURN_WITH_FINE = 'return-with-fine',
  PAY = 'pay'
}