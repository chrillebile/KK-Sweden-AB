export default interface PalletModel {
  id: number;
  amount: number;
  productionDate: string | null;
  location: string | null;
  deliveryTime: number | null;
  blocked: boolean;
}
