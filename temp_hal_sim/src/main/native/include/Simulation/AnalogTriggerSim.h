#pragma once

#ifndef __FRC_ROBORIO__

#include "MockData/AnalogTriggerData.h"
#include <memory>
#include <utility>
#include "CallbackStore.h"

namespace frc {
namespace sim {
class AnalogTriggerSim {
 public:
  explicit AnalogTriggerSim(int index) {
    m_index = index;
  }

  std::unique_ptr<CallbackStore> RegisterInitializedCallback(NotifyCallback callback, bool initialNotify) {
    auto store = std::make_unique<CallbackStore>(m_index, -1, callback, &HALSIM_CancelAnalogTriggerInitializedCallback);
    store->SetUid(HALSIM_RegisterAnalogTriggerInitializedCallback(m_index, &CallbackStoreThunk, store.get(), initialNotify));
    return std::move(store);
  }
  bool GetInitialized() {
    return HALSIM_GetAnalogTriggerInitialized(m_index);
  }
  void SetInitialized(bool initialized) {
    HALSIM_SetAnalogTriggerInitialized(m_index, initialized);
  }

  std::unique_ptr<CallbackStore> RegisterTriggerLowerBoundCallback(NotifyCallback callback, bool initialNotify) {
    auto store = std::make_unique<CallbackStore>(m_index, -1, callback, &HALSIM_CancelAnalogTriggerTriggerLowerBoundCallback);
    store->SetUid(HALSIM_RegisterAnalogTriggerTriggerLowerBoundCallback(m_index, &CallbackStoreThunk, store.get(), initialNotify));
    return std::move(store);
  }
  double GetTriggerLowerBound() {
    return HALSIM_GetAnalogTriggerTriggerLowerBound(m_index);
  }
  void SetTriggerLowerBound(double triggerLowerBound) {
    HALSIM_SetAnalogTriggerTriggerLowerBound(m_index, triggerLowerBound);
  }

  std::unique_ptr<CallbackStore> RegisterTriggerUpperBoundCallback(NotifyCallback callback, bool initialNotify) {
    auto store = std::make_unique<CallbackStore>(m_index, -1, callback, &HALSIM_CancelAnalogTriggerTriggerUpperBoundCallback);
    store->SetUid(HALSIM_RegisterAnalogTriggerTriggerUpperBoundCallback(m_index, &CallbackStoreThunk, store.get(), initialNotify));
    return std::move(store);
  }
  double GetTriggerUpperBound() {
    return HALSIM_GetAnalogTriggerTriggerUpperBound(m_index);
  }
  void SetTriggerUpperBound(double triggerUpperBound) {
    HALSIM_SetAnalogTriggerTriggerUpperBound(m_index, triggerUpperBound);
  }

  void ResetData() {
    HALSIM_ResetAnalogTriggerData(m_index);
  }
 private:
  int m_index;
};
} // namespace sim
} // namespace frc
#endif // __FRC_ROBORIO__
