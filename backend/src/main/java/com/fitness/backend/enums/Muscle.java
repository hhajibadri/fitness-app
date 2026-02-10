package com.fitness.backend.enums;

public enum Muscle {

  // Chest
  PECTORALIS_MAJOR(MuscleGroup.CHEST),
  PECTORALIS_MINOR(MuscleGroup.CHEST),

  // Back
  LATISSIMUS_DORSI(MuscleGroup.BACK),
  TRAPEZIUS(MuscleGroup.BACK),
  RHOMBOIDS(MuscleGroup.BACK),
  ERECTOR_SPINAE(MuscleGroup.BACK),

  // Shoulder
  DELTOID_ANTERIOR(MuscleGroup.SHOULDERS),
  DELTOID_LATERAL(MuscleGroup.SHOULDERS),
  DELTOID_POSTERIOR(MuscleGroup.SHOULDERS),

  // Arm
  BICEP_BRACHII(MuscleGroup.ARMS),
  BRACHIALIS(MuscleGroup.ARMS),
  BRACHIORADIALIS(MuscleGroup.ARMS),
  TRICEPS_BRACHII(MuscleGroup.ARMS),

  // Legs
  QUADRICEPS_RECTUS_FEMORIS(MuscleGroup.LEGS),
  QUADRICEPS_VASTUS_LATERALIS(MuscleGroup.LEGS),
  QUADRICEPS_VASTUS_MEDIALIS(MuscleGroup.LEGS),

  HAMSTRINGS_BICEPS_FEMORIS(MuscleGroup.LEGS),
  HAMSTRINGS_SEMITENDINOSUS(MuscleGroup.LEGS),
  HAMSTRINGS_SEMIMEMBRANOSUS(MuscleGroup.LEGS),

  GLUTEUS_MAXIMUS(MuscleGroup.LEGS),
  GLUTEUS_MEDIUS(MuscleGroup.LEGS),

  CALVES_GASTROCNEMIUS(MuscleGroup.LEGS),
  CALVES_SOLEUS(MuscleGroup.LEGS),

  // Core
  RECTUS_ABDOMINIS(MuscleGroup.CORE),
  EXTERNAL_OBLIQUES(MuscleGroup.CORE),
  INTERNAL_OBLIQUES(MuscleGroup.CORE),
  TRANSVERSE_ABDOMINIS(MuscleGroup.CORE);

  private final MuscleGroup group;

  Muscle(MuscleGroup group) {
    this.group = group;
  }

  public MuscleGroup getGroup() {
    return group;
  }
}
