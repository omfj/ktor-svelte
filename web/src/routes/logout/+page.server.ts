import { redirect } from '@sveltejs/kit';
import type { Actions, PageServerLoad } from './$types';

export const load = (async ({ locals }) => {
	// Redirect to home page if user is logged in
	if (locals.user) {
		throw redirect(304, '/');
	}

	// Redirect to login page if user is not logged in
	throw redirect(303, '/login');
}) satisfies PageServerLoad;

export const actions = {
	default: async ({ cookies }) => {
		cookies.delete('session');

		throw redirect(303, '/');
	}
} satisfies Actions;
