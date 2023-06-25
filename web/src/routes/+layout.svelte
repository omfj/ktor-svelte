<script>
	import '../app.css';
	import Header from '../lib/components/Header.svelte';
</script>

<Header />

<main class="max-w-2xl w-full mx-auto px-3">
	<slot />
</main>
