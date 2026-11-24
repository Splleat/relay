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
        <div className="flex gap-3 px-5 py-1.5 transition-colors hover:bg-hover/60">
            {sender.imageUrl ? (
                <img
                    src={sender.imageUrl}
                    alt={`${sender.name} 프로필 이미지`}
                    className="size-9 shrink-0 rounded-lg object-cover"
                />
            ) : (
                <div
                    aria-hidden="true"
                    className="flex size-9 shrink-0 items-center justify-center rounded-lg bg-accent-soft text-sm font-semibold text-accent"
                >
                    {sender.name.at(0)}
                </div>
            )}
            <div className="min-w-0 flex-1">
                <div className="flex items-baseline gap-2">
                    <span className="text-sm font-semibold">{sender.name}</span>
                    <time
                        dateTime={message.createdAt}
                        className="text-xs text-muted"
                    >
                        {formatTime(message.createdAt)}
                    </time>
                </div>
                <p className="text-[15px] leading-relaxed break-words whitespace-pre-wrap">
                    {message.content}
                </p>
            </div>
        </div>
    );
}

function formatTime(date: string) {
    return new Date(date).toLocaleTimeString('ko-KR', {
        hour: 'numeric',
        minute: '2-digit',
    });
}
