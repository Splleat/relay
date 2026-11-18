import { useRequiredParam } from '../hooks/useRequiredParam.ts';
import { ChannelView } from '../components/channel/ChannelView.tsx';

export function ChannelPage() {
    const channelId = useRequiredParam('channelId');

    return <ChannelView key={channelId} channelId={channelId} />;
}
