<script lang="ts">
	import '../app.css';
	import { user } from '$lib/stores';
	import Header from '../lib/components/Header.svelte';
	import type { LayoutServerData } from './$types';

	export let data: LayoutServerData;

	$: user.set(data.user);
</script>

<Header />

<main class="max-w-2xl w-full mx-auto px-3">
	<slot />
</main>
