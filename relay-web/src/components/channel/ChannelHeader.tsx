import type { ChannelResponse } from '../../api/channel.ts';

export function ChannelHeader({ channel }: { channel: ChannelResponse }) {
    return <header>{channel.name ? channel.name : 'DM'}</header>;
}
