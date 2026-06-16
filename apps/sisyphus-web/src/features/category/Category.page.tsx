import { CategoryField } from './CategoryField.widget';
import { CategoryFormUnified } from './Category.form';
import { CustomCard } from '@/components/custom/customCard';
import {
  ResizableHandle,
  ResizablePanel,
  ResizablePanelGroup,
} from '@/components/ui/resizable';
import { useTranslation } from 'react-i18next';
import { useIsMobile } from '@/hooks/use-mobile';

const CategoryPage = () => {
  const { t } = useTranslation();
  const isMobile = useIsMobile();

  return (
    <section className="relative mx-auto max-w-[1680px] h-full lg:p-6 sm:p-4 p-2">
      <ResizablePanelGroup direction={isMobile ? 'vertical' : 'horizontal'}>
        <ResizablePanel>
          <CustomCard
            title={t('category.page.category')}
            className="h-full space-y-1"
            content={<CategoryField condition={true} />}
          />
        </ResizablePanel>
        <ResizableHandle withHandle />
        <ResizablePanel>
          <CustomCard
            title={t('category.page.add_edit')}
            className="h-full"
            content={<CategoryFormUnified />}
          />
        </ResizablePanel>
      </ResizablePanelGroup>
    </section>
  );
};

export default CategoryPage;
