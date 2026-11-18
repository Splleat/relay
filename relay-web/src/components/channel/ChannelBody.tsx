import { type MessageResponse } from '../../api/message.ts';

export function ChannelBody({ messages }: { messages: MessageResponse[] }) {
    return (
        <div>
            {messages.map((message) => (
                <p key={message.messageId}>{message.content}</p>
            ))}
        </div>
    );
}
