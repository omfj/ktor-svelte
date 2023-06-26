import { error } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';

export const load = (async ({ locals }) => {
	const user = locals.user;

	if (!user) {
		throw error(404, 'User not found');
	}

	return {
		user
	};
}) satisfies PageServerLoad;
