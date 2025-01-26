PhysicsCustomizer v0.0.1
===========
A mod that allows modifying hard-coded Minecraft physics behavior.

마인크래프트에 하드코딩된 물리엔진 작동 방식을 수정할 수 있게 해주는 모드입니다.


Supported Configurations
------
| ID                                | Type                | Description                                                                                                                                                                                              | Default              |
|-----------------------------------|---------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------|
| `allow_45_strafe`                 | `boolean`           | Allow 45 strafing.                                                                                                                                                                                       | `true`               |
| `allow_sneak_sprint`              | `boolean`           | Allow sprinting while sneaking.                                                                                                                                                                          | `true`               |
| `allow_blip`                      | `boolean`           | Allow blips to happen.                                                                                                                                                                                   | `false`              |
| `delay_midair_sprint`             | `boolean`           | Delay mid-air sprint from registering by one tick.                                                                                                                                                       | `false`              |
| `expand_supporting_block_hitbox`  | `boolean`           | Expand the hitbox of supporting block checking to full block. This is used for blocks like Slime Block, Ice or Soul Sand(not using collision).                                                           | `true`               |
| `soul_sand_use_collision_instead` | `boolean`           | Use collision checking instead of supporting block checking for applying the effect of Soul Sand.                                                                                                        | `false`              |
| `thinner_ladder`                  | `boolean`           | Make ladders 2px thick instead of 3px.                                                                                                                                                                   | `false`              |
| `allow_climbing_by_jump`          | `boolean`           | Allow climbing ladders or vines by holding the jump key.                                                                                                                                                 | `true`               |
| `no_drag`                         | `boolean`           | Disable friction.                                                                                                                                                                                        | `false`              |
| `pre_1_13_swimming`               | `boolean`           | Use pre-1.13 swimming mechanism when in water.                                                                                                                                                           | `false`              |
| `disable_soft_collision`          | `boolean`           | Do not allow sprinting when the player collides with the wall 'softly', i.e. the angle of incidence is less than 8°.                                                                                     | `false`              |
| `use_old_pane_hitbox`             | `boolean`           | Use pre-1.9 pane hitbox, and make panes not connected to anything appear as cross shape.                                                                                                                 | `false`              |
| `f3_coordinates_precision`        | `int`               | Set the maximum number of decimal places to display in the 'XYZ' label of F3 debug screen.                                                                                                               | `5`                  |
| `inertia_threshold`               | `double`            | Set the value of inertia threshold/negligible speed threshold.                                                                                                                                           | `0.003`              |
| `collision_hitbox_contraction`    | `double`            | Set how much should the hitbox contract when collision checking. This is used for blocks like Cobweb, Honey Block or Soul Sand(using collision).                                                         | `0.00001F`           |
| `wall_collision_rule`             | `WallCollisionRule` | Set the strategy to check for wall collisions. Allowed values are: `faster_then_slower`(default for >=1.14), `slower_then_faster`, `x_then_z`(default for <1.14), `z_then_x`, `x_and_z`, `x_or_z`.       | `faster_then_slower` |
| `blip_fallback_mode`              | `BlipFallbackMode`  | Set the value to use when no steppable blocks are found while stepping checking. Allowed values are: `none`(default for >=1.20), `blip_down`(default for <1.20 and >=1.14), `blip_up`(default for <1.14) | `none`               |

*Note: The default behavior should be the same as vanilla 1.21.4. If there are any errors, please create a new issue.*

*참고: 기본 상태라면 바닐라 1.21.4와 같은 방식으로 작동합니다. 만약 예외가 있다면 Issue를 제보해주시면 감사하겠습니다.*
