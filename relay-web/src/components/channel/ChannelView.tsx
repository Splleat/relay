import { useCallback, useEffect, useState } from 'react';
import { type ChannelResponse, getChannel } from '../../api/channel.ts';
import { getChannelMessages, type MessageResponse } from '../../api/message.ts';
import { ChannelHeader } from './ChannelHeader.tsx';
import { ChannelBody } from './ChannelBody.tsx';
import { MessageInput } from './MessageInput.tsx';
import { subscribeChannel } from '../../api/stomp.ts';

export function ChannelView({ channelId }: { channelId: string }) {
    const [channel, setChannel] = useState<ChannelResponse | null>(null);
    const [messages, setMessages] = useState<MessageResponse[]>([]);

    const addMessage = useCallback((message: MessageResponse) => {
        setMessages((prev) => {
            const existsMessage = prev
                .map((m) => m.messageId)
                .includes(message.messageId);

            if (existsMessage) return prev;

            return [...prev, message];
        });
    }, []);

    useEffect(() => {
        return subscribeChannel(channelId, addMessage);
    }, [channelId, addMessage]);

    const loadChannel = useCallback(() => {
        getChannel(channelId).then(setChannel);
    }, [channelId]);

    const loadMessage = useCallback(() => {
        getChannelMessages(channelId).then((response) => {
            setMessages(response.messages);
        });
    }, [channelId]);

    useEffect(() => {
        loadChannel();
        loadMessage();
    }, [loadChannel, loadMessage]);

    if (!channel) {
        return null;
    }

    return (
        <div className="flex min-h-0 flex-1 flex-col">
            <ChannelHeader channel={channel} />
            <ChannelBody
                channelMembers={channel.channelMemberList}
                messages={messages}
            />
            <MessageInput channelId={channelId} onSent={addMessage} />
        </div>
    );
}
