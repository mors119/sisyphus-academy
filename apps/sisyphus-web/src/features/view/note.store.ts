import { create } from 'zustand';
import { NoteResponse, SortOption } from '../quick_edit/note.types';

const createEmptyNote = (): NoteResponse => ({
  id: 0,
  title: '',
  subTitle: '',
  description: '',
  tags: [],
  createdAt: '',
  category: { id: 0, title: '', color: '' },
  image: [],
});

interface NoteStore {
  editNote: NoteResponse;
  setEditNote: (note: NoteResponse) => void;
  resetEditNote: () => void;

  sortOption: SortOption;
  setSortOption: (option: SortOption) => void;
  toggleSortField: (field: string) => void;
}

export const useNoteStore = create<NoteStore>()((set) => ({
  editNote: createEmptyNote(),
  setEditNote: (note) => set({ editNote: note }),
  resetEditNote: () => set({ editNote: createEmptyNote() }),

  sortOption: { field: 'createdAt', order: 'desc' },
  setSortOption: (options) => set({ sortOption: options }),
  toggleSortField: (field) =>
    set((state) => {
      const isSameField = state.sortOption.field === field;
      return {
        sortOption: {
          field,
          order:
            isSameField && state.sortOption.order === 'asc' ? 'desc' : 'asc',
        },
      };
    }),
}));
