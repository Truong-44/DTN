export interface ChatSession {
  id?: number;
  taiKhoanId?: number;
  sessionType: 'USER' | 'GUEST';
  status: 'ACTIVE' | 'ENDED';
  createdAt?: Date;
  endedAt?: Date;
}

export interface ChatMessage {
  id?: number;
  sessionId: number;
  content: string;
  messageType: 'USER' | 'BOT';
  timestamp?: Date;
  metadata?: any;
}

export interface BotSuggestion {
  id?: number;
  content: string;
  suggestionType: 'PRODUCT' | 'CATEGORY' | 'GENERAL';
  relatedProductId?: number;
  confidence: number;
}

export interface ProductSuggestionResponse {
  sanpham: any;
  reason: string;
  confidence: number;
  relevanceScore?: number;
}

export interface ChatSessionResponse {
  sessions: ChatSession[];
  totalPages: number;
  totalElements: number;
  currentPage: number;
}

export interface MessageResponse {
  messages: ChatMessage[];
  totalPages: number;
  totalElements: number;
  currentPage: number;
}
