import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private products = [
    {
      id: '1',
      name: 'Bàn ăn gỗ tự nhiên',
      price: 5000000,
      description: 'Bàn ăn gỗ óc chó cao cấp, thiết kế hiện đại.',
      image: 'https://via.placeholder.com/300',
    },
    {
      id: '2',
      name: 'Ghế ăn bọc da',
      price: 2000000,
      description: 'Ghế ăn bọc da cao cấp, màu đen sang trọng.',
      image: 'https://via.placeholder.com/300',
    },
    {
      id: '3',
      name: 'Khăn trải bàn hoa văn',
      price: 300000,
      description: 'Khăn trải bàn cotton, hoa văn tinh tế.',
      image: 'https://via.placeholder.com/300',
    },
    {
      id: '4',
      name: 'Bộ bàn ghế phòng ăn',
      price: 15000000,
      description: 'Bộ bàn ghế 6 người, phong cách cổ điển.',
      image: 'https://via.placeholder.com/300',
    },
  ];

  private normalizeText(text: string): string {
    const map: { [key: string]: string } = {
      àáảãạ: 'a',
      èéẻẽẹ: 'e',
      ìíỉĩị: 'i',
      òóỏõọ: 'o',
      ùúủũụ: 'u',
      ỳýỷỹỵ: 'y',
      đ: 'd',
      ÀÁẢÃẠ: 'A',
      ÈÉẺẼẸ: 'E',
      ÌÍỈĨỊ: 'I',
      ÒÓỎÕỌ: 'O',
      ÙÚỦŨỤ: 'U',
      ỲÝỶỸỴ: 'Y',
      Đ: 'D',
    };
    let normalized = text.toLowerCase().replace(/\s+/g, '');
    for (const [chars, replacement] of Object.entries(map)) {
      for (const char of chars) {
        normalized = normalized.replace(new RegExp(char, 'g'), replacement);
      }
    }
    return normalized;
  }

  getProducts() {
    return this.products;
  }

  getProductById(id: string) {
    return this.products.find((p) => p.id === id);
  }

  searchProducts(query: string) {
    query = query
      .toLowerCase()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, ''); // Chuẩn hóa không dấu
    return this.products.filter((p) =>
      p.name
        .toLowerCase()
        .normalize('NFD')
        .replace(/[\u0300-\u036f]/g, '')
        .includes(query)
    );
  }
}
