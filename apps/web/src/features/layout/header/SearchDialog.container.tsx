import {
  CommandDialog,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from '@/components/ui/command';
import { useState } from 'react';
import { useSearchQuery } from './search.query';
import { Loader } from '@/components/custom/Loader';
import { useTranslation } from 'react-i18next';
import { ErrorState } from '@/components/custom/Error';
import { SearchResponse } from './search.type';
import { Book, Folder, Tag } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { SEARCH_ITEM } from './search.constants';
import { Command } from 'cmdk';

interface SearchDialogProps {
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

export const SearchDialog = ({ open, setOpen }: SearchDialogProps) => {
  const [query, setQuery] = useState('');
  const typeOrder = Object.values(SEARCH_ITEM); // 정렬 순서
  const { t } = useTranslation();
  const { data, isLoading, isError } = useSearchQuery(query);
  const navigate = useNavigate();

  const typeIcon = (type: string, num: number) => {
    switch (type) {
      case SEARCH_ITEM.NOTE:
        return num === 2 ? '• ' : <Book size={10} />;

      case SEARCH_ITEM.TAG:
        return num === 2 ? '# ' : <Tag size={10} />;

      case SEARCH_ITEM.CATE:
        return num === 2 ? '- ' : <Folder size={10} />;

      default:
        return null;
    }
  };
  // console.log(data);
  const handleSelectItem = (item: SearchResponse) => {
    if (item.type == SEARCH_ITEM.NOTE) {
      navigate(
        `/view?mode=search&type=NOTE&id=${item.id}&title=${encodeURIComponent(
          item.title,
        )}`,
      );
    } else {
      navigate(`/view?mode=search&type=${item.type}&id=${item.id}`);
    }
    setOpen(false);
  };

  return (
    <CommandDialog open={open} onOpenChange={setOpen}>
      <Command shouldFilter={false}>
        <CommandInput
          placeholder={t('search.place')}
          value={query}
          onValueChange={setQuery}
        />
        <CommandList>
          {isError ? (
            <ErrorState />
          ) : isLoading ? (
            <Loader />
          ) : data && data.length > 0 ? (
            Object.entries(
              data.reduce<Record<string, SearchResponse[]>>((acc, item) => {
                if (!acc[item.type]) acc[item.type] = [];
                acc[item.type].push(item);
                return acc;
              }, {}),
            )
              .sort(
                ([a], [b]) =>
                  typeOrder.indexOf(a as SEARCH_ITEM) -
                  typeOrder.indexOf(b as SEARCH_ITEM),
              )
              .map(([type, items]) => (
                <CommandGroup
                  key={`${type}-${items.length}`}
                  heading={t(`item.${type}`, { defaultValue: type })}>
                  {items.map((item) => (
                    <CommandItem
                      key={`${item.type}-${item.id}`}
                      value={item.title}
                      onSelect={() => handleSelectItem(item)}
                      className="cursor-pointer flex items-center gap-2">
                      {typeIcon(item.type, 1)}
                      <span>{item.title}</span>
                    </CommandItem>
                  ))}
                </CommandGroup>
              ))
          ) : (
            <CommandEmpty>{t('search.no_result')}</CommandEmpty>
          )}
        </CommandList>
      </Command>
    </CommandDialog>
  );
};
