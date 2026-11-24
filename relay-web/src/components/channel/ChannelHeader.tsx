import type { ChannelResponse } from '../../api/channel.ts';

export function ChannelHeader({ channel }: { channel: ChannelResponse }) {
    return (
        <header className="flex h-14 shrink-0 items-center justify-between border-b border-border px-5">
            <h1 className="flex min-w-0 items-center gap-1.5 font-semibold">
                {channel.name && <span className="text-muted">#</span>}
                <span className="truncate">
                    {channel.name ? channel.name : 'DM'}
                </span>
            </h1>
            <span className="shrink-0 text-xs text-muted">
                멤버 {channel.channelMemberList.length}명
            </span>
        </header>
    );
}
