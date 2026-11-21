import { type MessageResponse } from '../../api/message.ts';
import { MessageItem } from '../message/MessageItem.tsx';
import type { ChannelMemberResponse } from '../../api/channel.ts';

export function ChannelBody({
    channelMembers,
    messages,
}: {
    channelMembers: ChannelMemberResponse[];
    messages: MessageResponse[];
}) {
    return (
        <div>
            {messages.map((message) => (
                <MessageItem
                    key={message.messageId}
                    sender={findSender(message.senderId, channelMembers)}
                    message={message}
                />
            ))}
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
