export type ActivityLevel = 'LOW' | 'MODERATE' | 'HIGH';
export type Goal = 'FAT_LOSS' | 'MAINTENANCE';
export type Budget = 'LOW' | 'MEDIUM' | 'HIGH';
export type DietStyle = 'OMNIVORE' | 'VEGETARIAN';
export type MealType = 'BREAKFAST' | 'LUNCH' | 'DINNER';

export interface OnboardingRequest {
  displayName: string;
  heightCm: number;
  weightKg: number;
  activityLevel: ActivityLevel;
  goal: Goal;
  mealsPerDay: 3;
  budget: Budget;
  cookTimeMinutes: number;
  dietStyle: DietStyle;
  allergens: string[];
}

export interface CheckInRequest {
  complianceScore: number;  // 0..1
  hungerEvening: number;    // 0..1
  energyAfterMeals: number; // 0..1
  sleepScore: number;       // 0..1
  cravings: number;         // 0..1
}

export interface RecipeView {
  id: string;
  name: string;
  suggestedMealType: MealType;
  prepMinutes: number;
  tags: string[];
  calories: number;
  proteinG: number;
  carbsG: number;
  fatG: number;
}

export interface MealPlanItemView {
  mealType: MealType;
  recipe: RecipeView;
  notes: string;
}

export interface MealPlanDayView {
  dayIndex: number;
  items: MealPlanItemView[];
}

export interface MealPlanResponse {
  planId: string;
  userId: string;
  weekStart: string;
  strategy: string;
  days: MealPlanDayView[];
}

export interface OnboardingResponse {
  userId: string;
  mealPlan: MealPlanResponse;
}

