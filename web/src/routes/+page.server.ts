import { getHappenings } from '$lib/api/happenings';
import type { PageServerLoad } from './$types';

export const load = (async () => {
	const happenings = await getHappenings();

	return {
		happenings
	};
}) satisfies PageServerLoad;
