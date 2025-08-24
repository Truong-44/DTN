// Authentication guards
export * from './auth.guard';

// Authorization guards
export * from './role.guard';
export * from './permission.guard';

// Data validation guards
export * from './data.guard';

// Form guards
export * from './unsaved-changes.guard';

// Feature guards
export * from './feature.guard';

// Combine multiple guards helper
export function combineGuards(...guards: any[]): any {
  return (route: any, state: any) => {
    for (const guard of guards) {
      const result = guard(route, state);
      if (result === false) {
        return false;
      }
    }
    return true;
  };
}
