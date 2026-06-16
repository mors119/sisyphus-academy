import { CustomCard } from '@/components/custom/customCard';
import { ViewFormField } from '../view/ViewForm.container';
import { useNoteStore } from '../view/note.store';
import { useEffect } from 'react';

const AddPage = () => {
  const resetEditNote = useNoteStore((s) => s.resetEditNote);

  useEffect(() => {
    // ViewPage를 떠날 때(언마운트) 초기화
    return () => {
      resetEditNote();
    };
  }, [resetEditNote]);

  return (
    <section className=" max-w-[1280px] mx-auto xl:px-24 xl:py-30 lg:px-20 lg:py-20 sm:px-10 sm:py-10 px-2 py-6">
      <div className="w-full">
        <CustomCard content={<ViewFormField />} />
      </div>
    </section>
  );
};

export default AddPage;
