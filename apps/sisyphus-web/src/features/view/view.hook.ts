import { useEffect, useState } from 'react';

export function useLocalStorageBoolean(key: string, defaultValue: boolean) {
  const [value, setValue] = useState<boolean>(() => {
    if (typeof window === 'undefined') return defaultValue;

    const stored = localStorage.getItem(key);
    return stored !== null ? stored === 'true' : defaultValue;
  });

  useEffect(() => {
    localStorage.setItem(key, String(value));
  }, [key, value]);

  return [value, setValue] as const;
}
