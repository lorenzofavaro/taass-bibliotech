export interface Product {
  id: number;
  title: string;
  author: string;
  stock: number;
  categories: Category[];
  description: string;
  picture: string;
}

export interface Category {
  id: number;
  name: string;
}
