export interface Trend {
  id: number;
  title: string;
  description: string;
  image_url: string;
  category: string;
  date: Date;
  tags: string[];
  featured?: boolean;
  status?: boolean;
  author?: string;
  readTime?: number;
}

export interface TrendResponse {
  trends: Trend[];
  total: number;
  page: number;
  size: number;
}
