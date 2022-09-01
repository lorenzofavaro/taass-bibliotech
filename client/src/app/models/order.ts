export interface Order {
  id: string;
  userId: number;
  total: number;
  orderStatus: string;
  paymentStatus: string;
  products: OrderItem[];
  date: Date;
}

export interface OrderItem {
  id: number;
  productId: number;
  quantity: number;
  size: number;
}
