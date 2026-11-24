import { type MessageResponse } from '../../api/message.ts';
import { MessageItem } from '../message/MessageItem.tsx';
import type { ChannelMemberResponse } from '../../api/channel.ts';
import { useEffect, useRef } from 'react';

export function ChannelBody({
    channelMembers,
    messages,
    hasPrev,
    loadPrevMessages,
}: {
    channelMembers: ChannelMemberResponse[];
    messages: MessageResponse[];
    hasPrev: boolean;
    loadPrevMessages: () => void;
}) {
    const containerRef = useRef<HTMLDivElement | null>(null);
    const sentinelRef = useRef<HTMLDivElement | null>(null);

    useEffect(() => {
        const target = sentinelRef.current;

        if (!target) return;

        let timerId: ReturnType<typeof setTimeout> | undefined;

        const observer = new IntersectionObserver(
            (entries) => {
                if (entries[0].isIntersecting) {
                    timerId = setTimeout(() => {
                        loadPrevMessages();
                    }, 100);
                }
            },
            {
                root: containerRef.current,
                rootMargin: '20px 0px 0px 0px',
                threshold: 1,
            },
        );

        observer.observe(target);

        return () => {
            clearTimeout(timerId);
            observer.disconnect();
        };
    }, [loadPrevMessages]);

    return (
        <div
            className="flex min-h-0 flex-1 flex-col-reverse overflow-y-auto"
            ref={containerRef}
        >
            <div className="flex flex-col py-4">
                {messages.length === 0 && (
                    <p className="px-5 py-10 text-center text-sm text-muted">
                        아직 메시지가 없습니다. 첫 메시지를 보내 보세요.
                    </p>
                )}
                {hasPrev && <div ref={sentinelRef}></div>}
                {messages.map((message) => (
                    <MessageItem
                        key={message.messageId}
                        sender={findSender(message.senderId, channelMembers)}
                        message={message}
                    />
                ))}
            </div>
        </div>
    );
}

function findSender(senderId: string, channelMembers: ChannelMemberResponse[]) {
    return (
        channelMembers.find((m) => m.userId === senderId) ?? {
            userId: '',
            name: '알 수 없는 사용자',
            imageUrl: null,
        }
    );
}
