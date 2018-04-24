export default interface PalletModel {
  id: number;
  productionDate: string | null;
  location: string | null;
  deliveryTime: number | null;
  blocked: boolean;
}
