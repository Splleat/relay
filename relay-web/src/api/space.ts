import { fetchApi } from './client.ts';

export interface JoinedSpacesResponse {
    spaces: SpaceSummaryResponse[];
}

export interface SpaceSummaryResponse {
    id: string;
    name: string;
    type: SpaceType;
}

export interface SpaceResponse {
    id: string;
    name: string;
    type: SpaceType;
    channels: SpaceChannelSummaryResponse[];
}

export interface SpaceChannelSummaryResponse {
    id: string;
    name: string;
}

export interface CreateSpaceRequest {
    name: string;
}

export interface CreateSpaceChannelRequest {
    name: string;
}

export type SpaceType = 'PERSONAL' | 'SHARED';

export async function getJoinedSpaces(): Promise<JoinedSpacesResponse> {
    return await fetchApi<JoinedSpacesResponse>('/spaces');
}

export async function getSpace(spaceId: string): Promise<SpaceResponse> {
    return await fetchApi<SpaceResponse>(`/spaces/${spaceId}`);
}

export async function createSpace(request: CreateSpaceRequest) {
    await fetchApi<void>('/spaces', 'POST', request);
}

export async function createSpaceChannel(
    spaceId: string,
    request: CreateSpaceChannelRequest,
) {
    await fetchApi<void>(`/spaces/${spaceId}/channels`, 'POST', request);
}
