import { format } from 'date-fns';

export const formatDate = (date: Date | string) => {
	const d = new Date(date);

	return format(d, 'dd.MM.yyyy');
};
