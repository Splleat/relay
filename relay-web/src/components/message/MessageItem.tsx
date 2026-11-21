import type { MessageResponse } from '../../api/message.ts';
import type { ChannelMemberResponse } from '../../api/channel.ts';

export function MessageItem({
    sender,
    message,
}: {
    sender: ChannelMemberResponse;
    message: MessageResponse;
}) {
    return (
        <div className="flex flex-row gap-2">
            {sender.imageUrl ? (
                <img src={sender.imageUrl} alt={sender.name.at(0)}></img>
            ) : (
                sender.name.at(0)
            )}
            <div>{sender.name}</div>
            <div>{message.content}</div>
            <div>{formatTime(message.createdAt)}</div>
        </div>
    );
}

function formatTime(date: string) {
    return new Date(date).toLocaleTimeString('ko-KR');
}
