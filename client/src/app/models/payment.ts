export interface Payment {
  id: number;
  orderId: string;
  userId: number;
  type: string;
  paypalPaymentId: string;
  cardName: string;
  cardNumber: string;
  expiryMonth: number;
  expiryYear: number;
  cvc: number;
  holderName: string;
  paymentStatus: string;
  total: number;
}
