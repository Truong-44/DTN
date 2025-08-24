export interface Blog {
  id: number;
  title: string;
  description: string;
  content?: string;
  image: string;
  author: string;
  date: Date;
  readTime: number;
  category: string;
  tags: string[];
  featured: boolean;
  status?: boolean;
  views?: number;
  likes?: number;
}

export interface BlogResponse {
  blogs: Blog[];
  total: number;
  page: number;
  size: number;
}
