import { PUBLIC_BACKEND_URL } from '$env/static/public';
import { writable } from 'svelte/store';

export type User = {
	id: string;
	username: string;
	email: string;
};

export const user = writable<User | null>(null);

/**
 * Registers the user by sending a POST request to the server, and setting the user store to the user.
 *
 * @param username the username of the user
 * @param email the email of the user
 * @param password the password of the user
 * @returns the user if the user is logged in, null otherwise
 */
export const register = async (username: string, email: string, password: string) => {
	try {
		const response = await fetch(PUBLIC_BACKEND_URL + '/register', {
			method: 'POST',
			credentials: 'include',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				username,
				email,
				password
			})
		});

		if (!response.ok) return null;

		return await getUser();
	} catch (error) {
		console.error(error);
	}
};

/**
 * Logs in the user by sending a POST request to the server, and setting the user store to the user.
 *
 * @param username username of the user
 * @param password password of the user
 * @returns the user if the user is logged in, null otherwise
 */
export const login = async (username: string, password: string) => {
	try {
		const response = await fetch(PUBLIC_BACKEND_URL + '/login', {
			method: 'POST',
			credentials: 'include',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				username,
				password
			})
		});

		if (!response.ok) return null;

		return await getUser();
	} catch (error) {
		console.error(error);
	}
};

/**
 * Gets the user from the server and sets the user store to the user.
 *
 * @returns the user if the user is logged in, null otherwise
 */
export const getUser = async () => {
	try {
		const response = await fetch(PUBLIC_BACKEND_URL + '/users/me', {
			method: 'GET',
			credentials: 'include'
		});

		if (!response.ok) return null;

		const data = (await response.json()) as User;

		user.set(data);

		return data;
	} catch (error) {
		console.error(error);
	}
};

/**
 * Logs out the user by sending a POST request to the server, and setting the user store to null.
 *
 * @returns true if logout was successful, false otherwise
 */
export const logout = async () => {
	try {
		const response = await fetch(PUBLIC_BACKEND_URL + '/logout', {
			method: 'POST',
			credentials: 'include'
		});

		if (!response.ok) return false;

		user.set(null);

		return true;
	} catch (error) {
		console.error(error);
	}
};
