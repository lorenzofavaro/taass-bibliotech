export interface Cart {
  id: string;
  idUser: number;
  grandTotal: number;
  products: CartItem[];
}

export interface CartItem {
  id: number;
  productId: number;
  quantity: number;
}
