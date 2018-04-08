export default interface PalletModel {
  id: number;
  amount: number;
  productionDate: string | null;
  location: string | null;
  deliveryTime: string | null;
  blocked: boolean;
}
