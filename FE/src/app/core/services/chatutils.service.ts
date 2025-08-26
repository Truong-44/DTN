import { Injectable } from '@angular/core';
import { ChatMessage } from '../models/chat.model';

@Injectable({
  providedIn: 'root',
})
export class ChatUtilsService {
  // Message formatting utilities
  formatMessageTime(timestamp: Date | string | undefined): string {
    if (!timestamp) return '';

    const date = new Date(timestamp);
    const now = new Date();
    const diffInHours = (now.getTime() - date.getTime()) / (1000 * 60 * 60);

    if (diffInHours < 1) {
      return date.toLocaleTimeString('vi-VN', {
        hour: '2-digit',
        minute: '2-digit',
      });
    } else if (diffInHours < 24) {
      return date.toLocaleTimeString('vi-VN', {
        hour: '2-digit',
        minute: '2-digit',
      });
    } else {
      return date.toLocaleDateString('vi-VN', {
        day: '2-digit',
        month: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
      });
    }
  }

  // Message content utilities
  isMessageFromToday(timestamp: Date | string | undefined): boolean {
    if (!timestamp) return false;

    const messageDate = new Date(timestamp);
    const today = new Date();

    return messageDate.toDateString() === today.toDateString();
  }

  // Message validation
  isValidMessage(content: string): boolean {
    return content.trim().length > 0 && content.trim().length <= 1000;
  }

  // Extract mentions or special commands from message
  extractMentions(content: string): string[] {
    const mentionRegex = /@(\w+)/g;
    const mentions = [];
    let match;

    while ((match = mentionRegex.exec(content)) !== null) {
      mentions.push(match[1]);
    }

    return mentions;
  }

  // Check if message contains product-related keywords
  isProductQuery(content: string): boolean {
    const productKeywords = [
      'sản phẩm',
      'mua',
      'giá',
      'tìm',
      'bàn',
      'ghế',
      'tủ',
      'giường',
      'đèn',
      'sofa',
      'kệ',
      'nội thất',
      'chất lượng',
      'màu sắc',
      'kích thước',
      'material',
      'giảm giá',
      'khuyến mãi',
    ];

    const lowerContent = content.toLowerCase();
    return productKeywords.some((keyword) =>
      lowerContent.includes(keyword.toLowerCase())
    );
  }

  // Generate message preview (for notifications)
  generateMessagePreview(content: string, maxLength = 50): string {
    if (content.length <= maxLength) {
      return content;
    }
    return content.substring(0, maxLength) + '...';
  }

  // Chat session utilities
  generateSessionTitle(messages: ChatMessage[]): string {
    if (messages.length === 0) return 'Cuộc trò chuyện mới';

    const firstUserMessage = messages.find((m) => m.messageType === 'USER');
    if (firstUserMessage) {
      return this.generateMessagePreview(firstUserMessage.content, 30);
    }

    return 'Cuộc trò chuyện mới';
  }

  // Message grouping (group messages by time periods)
  groupMessagesByTime(messages: ChatMessage[]): {
    [key: string]: ChatMessage[];
  } {
    const groups: { [key: string]: ChatMessage[] } = {};

    messages.forEach((message) => {
      if (!message.timestamp) return;

      const date = new Date(message.timestamp);
      const today = new Date();
      const yesterday = new Date(today);
      yesterday.setDate(yesterday.getDate() - 1);

      let groupKey: string;

      if (date.toDateString() === today.toDateString()) {
        groupKey = 'Hôm nay';
      } else if (date.toDateString() === yesterday.toDateString()) {
        groupKey = 'Hôm qua';
      } else {
        groupKey = date.toLocaleDateString('vi-VN', {
          weekday: 'long',
          year: 'numeric',
          month: 'long',
          day: 'numeric',
        });
      }

      if (!groups[groupKey]) {
        groups[groupKey] = [];
      }
      groups[groupKey].push(message);
    });

    return groups;
  }

  // Auto-scroll utilities
  shouldAutoScroll(element: HTMLElement): boolean {
    const threshold = 100; // pixels from bottom
    return (
      element.scrollHeight - element.scrollTop - element.clientHeight <
      threshold
    );
  }

  scrollToBottom(element: HTMLElement, smooth = true): void {
    setTimeout(() => {
      element.scrollTo({
        top: element.scrollHeight,
        behavior: smooth ? 'smooth' : 'auto',
      });
    }, 100);
  }

  // Local storage utilities for chat
  saveChatSession(sessionId: number, data: any): void {
    try {
      localStorage.setItem(`chat_session_${sessionId}`, JSON.stringify(data));
    } catch (error) {
      console.error('Failed to save chat session to localStorage:', error);
    }
  }

  loadChatSession(sessionId: number): any {
    try {
      const data = localStorage.getItem(`chat_session_${sessionId}`);
      return data ? JSON.parse(data) : null;
    } catch (error) {
      console.error('Failed to load chat session from localStorage:', error);
      return null;
    }
  }

  clearChatSession(sessionId: number): void {
    try {
      localStorage.removeItem(`chat_session_${sessionId}`);
    } catch (error) {
      console.error('Failed to clear chat session from localStorage:', error);
    }
  }
}
