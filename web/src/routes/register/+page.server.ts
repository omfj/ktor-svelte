import { redirect } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';
import type { Actions } from './$types';
import { PUBLIC_BACKEND_URL } from '$env/static/public';

export const load = (async ({ locals }) => {
	if (locals.user) {
		throw redirect(304, '/');
	}
}) satisfies PageServerLoad;

export const actions = {
	default: async ({ request, cookies }) => {
		const data = await request.formData();

		const username = data.get('username');
		const email = data.get('email');
		const password = data.get('password');
		const passwordConfirm = data.get('passwordConfirm');

		if (password !== passwordConfirm) {
			return {
				status: 400,
				success: false,
				message: 'Passwords do not match'
			};
		}

		const response = await fetch(PUBLIC_BACKEND_URL + '/register', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				username,
				email,
				password
			})
		});

		if (!response.ok) {
			return {
				status: 400,
				success: false,
				message: 'Username or email already exists'
			};
		}

		cookies.set('session', await response.text());

		throw redirect(303, '/');
	}
} satisfies Actions;
