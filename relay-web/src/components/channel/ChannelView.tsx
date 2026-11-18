import { useCallback, useEffect, useState } from 'react';
import { type ChannelResponse, getChannel } from '../../api/channel.ts';
import { getChannelMessages, type MessageResponse } from '../../api/message.ts';
import { ChannelHeader } from './ChannelHeader.tsx';
import { ChannelBody } from './ChannelBody.tsx';
import { MessageInput } from './MessageInput.tsx';

export function ChannelView({ channelId }: { channelId: string }) {
    const [channel, setChannel] = useState<ChannelResponse | null>(null);
    const [messages, setMessages] = useState<MessageResponse[]>([]);

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

    function handleSendMessage(message: MessageResponse) {
        setMessages((prev) => [...prev, message]);
    }

    return (
        <div className="flex h-full flex-col">
            <ChannelHeader channel={channel} />
            <ChannelBody messages={messages} />
            <MessageInput channelId={channelId} onSent={handleSendMessage} />
        </div>
    );
}
