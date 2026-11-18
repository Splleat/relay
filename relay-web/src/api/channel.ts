import { fetchApi } from './client.ts';

type ChannelType = 'SPACE' | 'DM' | 'GROUP_DM';

export interface ChannelResponse {
    id: string;
    channelType: ChannelType;
    name: string | null;
    channelMemberList: ChannelMemberResponse[];
}

export interface ChannelMemberResponse {
    userId: string;
    name: string;
    imageUrl: string | null;
}

export async function getChannel(channelId: string): Promise<ChannelResponse> {
    return await fetchApi<ChannelResponse>(`/channels/${channelId}`);
}
